var Util = {}
// 分页的处理类
Util.formHttpRequestTransform = function (data) {
  /**
   * The workhorse; converts an object to x-www-form-urlencoded serialization.
   * @param {Object} obj
   * @return {String}
   */
  var param = function (obj) {
    var query = ''
    var name, value, fullSubName, subName, subValue, innerObj, i

    for (name in obj) {
      if (obj.hasOwnProperty(name) && !name.startsWith('$$')) {
        value = obj[name]

        if (value instanceof Array) {
          for (i = 0; i < value.length; ++i) {
            subValue = value[i]
            fullSubName = name + '[' + i + ']'
            innerObj = {}
            innerObj[fullSubName] = subValue
            query += param(innerObj) + '&'
          }
        } else if (value instanceof Object) {
          for (subName in value) {
            if (value.hasOwnProperty(subName)) {
              subValue = value[subName]
              fullSubName = name + '[' + subName + ']'
              innerObj = {}
              innerObj[fullSubName] = subValue
              query += param(innerObj) + '&'
            }
          }
        } else if (value !== undefined && value !== null) {
          query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&'
        }
      }
    }
    return query.length ? query.substr(0, query.length - 1) : query
  }
  return data !== null && typeof data === 'object' && String(data) !== '[object File]' ? param(data) : data
}

Util.escapeHTML = function (text) {
  return !text ? '' : text.replace(/["'/<>]/g, function (a) {
    return {'"': '&quot;', "'": '&#39;', '/': '&#47;', '<': '&lt;', '>': '&gt;'}[a]
  })
}

Util.capitalize = function (s) {
  return typeof s === 'string' ? s.charAt(0).toUpperCase() + s.slice(1) : s
}

Util.camelize = function (s) {
  return typeof s !== 'string' ? s
    : s.replace(/^([A-Z])|[\s\-_](\w)/g, function (match, p1, p2) {
      return p2 ? p2.toUpperCase() : p1.toLowerCase()
    })
}

Util.handleFailure = function (data) {
  // const data = response.data
  if (data && !data.success) {
    if (data.code === 1) {
      location.href = 'login?redirect=' + encodeURIComponent(location.href)
    } else {
      Util.alert(data.message || '服务访问出错')
    }
  } else {
    // Util.alert('服务访问出错')
    location.href = process.env.wwwPath + '#/?redirect=' + encodeURIComponent(location.hash.substring(1))
  }
}

Util.formatDate = function (date, fmt) {
  function padLeftZero(str) {
    return ('00' + str).substr(str.length)
  }

  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  let o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds()
  }
  for (let k in o) {
    if (new RegExp(`(${k})`).test(fmt)) {
      let str = o[k] + ''
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str))
    }
  }
  return fmt
}

Util.alert = alert

export default Util
