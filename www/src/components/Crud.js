import Page from './Page'

let Crud = function (R, successFunc, errorFunc) {
  let self = this
  const defaultErrorMessage = '访问错误'

  function simpleCopy (source) {
    let destination = {}
    let exclude = 'createTime|createUserId|updateTime|updateUserId|deleted'
    for (let key in source) {
      if (source.hasOwnProperty(key) && exclude.indexOf(key) < 0) {
        destination[key] = source[key]
      }
    }
    return destination
  }

  let onSuccess = function (data) {
    self.p.loading = false
    if (typeof successFunc === 'function') {
      successFunc(data)
    } else {
      if (data.success) {
        self.p.load()
      } else {
        alert(data.info || defaultErrorMessage)
      }
    }
  }

  let onError = function (ret) {
    self.p.loading = false
    if (typeof errorFunc === 'function') {
      errorFunc(ret)
    } else {
      if (ret !== null && typeof ret === 'object') {
        alert('Status[' + ret.status + ']: ' + (ret.data.info || ret.statusText || defaultErrorMessage))
      } else {
        alert(typeof ret === 'string' ? ret : defaultErrorMessage)
      }
    }
  }

  self.add = function () {
    self.record = {}
  }

  self.edit = function (record) {
    self.record = simpleCopy(record)
  }

  self.view = function (record) {
    self.record = record
  }

  self.save = function (record) {
    if (self.p.loading) {
      return
    }
    self.p.loading = true
    // R.save(record, onSuccess, onError)
    R.save({id: record.id}, record)
      .then(response => {
        // success callback
        onSuccess(response.body)
      }, response => {
        onError(response.body)
      }
    )
  }

  self.remove = function (record, message) {
    if (!confirm(message || '确定要删除这条记录吗?')) {
      return
    }
    R.remove({}, {id: record.id}, onSuccess, onError)
  }

  self.p = new Page(R.query)// .load()
  self.record = {}
}

export default Crud

