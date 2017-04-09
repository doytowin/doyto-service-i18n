import Page from './Page'

var Crud = function (R, successFunc, errorFunc) {
  var defaultErrorMessage = '访问错误'

  function simpleCopy (source) {
    var destination = {}
    var exclude = 'createTime|createUserId|updateTime|updateUserId|deleted'
    for (var key in source) {
      if (source.hasOwnProperty(key) && exclude.indexOf(key) < 0) {
        destination[key] = source[key]
      }
    }
    return destination
  }

  var onSuccess = function (data) {
    this.p.loading = false
    if (typeof successFunc === 'function') {
      successFunc(data)
    } else {
      if (!data.success) {
        alert(data.message || defaultErrorMessage)
      }
    }
  }

  var onError = function (ret) {
    this.p.loading = false
    if (typeof errorFunc === 'function') {
      errorFunc(ret)
    } else {
      if (typeof ret === 'object') {
        alert('Status[' + ret.status + ']: ' + (ret.data.message || ret.statusText || defaultErrorMessage))
      } else {
        alert(typeof ret === 'string' ? ret : defaultErrorMessage)
      }
    }
  }

  this.add = function () {
    this.record = {}
  }

  this.edit = function (record) {
    this.record = simpleCopy(record)
  }

  this.view = function (record) {
    this.record = record
  }

  this.save = function (record) {
    if (this.p.loading) {
      return
    }
    this.p.loading = true
    R.save(record, onSuccess, onError)
  }

  this.remove = function (record, message) {
    if (!confirm(message || '确定要删除这条记录吗?')) {
      return
    }
    R.remove({}, {id: record.id}, onSuccess, onError)
  }

  this.p = new Page(R.query)// .load()
}

export default Crud

