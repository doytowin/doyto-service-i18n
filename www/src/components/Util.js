import Vue from 'vue'

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

  return Vue.util.isObject(data) && String(data) !== '[object File]' ? param(data) : data
}

Util.escapeHTML = function (text) {
  return !text ? '' : text.replace(/["'/<>]/g, function (a) {
    return {'"': '&quot;', "'": '&#39;', '/': '&#47;', '<': '&lt;', '>': '&gt;'}[a]
  })
}

Util.capitalize = function (s) {
  return typeof s === 'string' ? s.charAt(0).toUpperCase() + s.slice(1) : s
}

Util.handleFailure = function (data) {
  if (data && !data.success) {
    if (data.code === 1) {
      location.href = 'login?redirect=' + encodeURIComponent(location.href)
    } else {
      alert(data.info || '访问错误')
    }
  }
}

export default Util
