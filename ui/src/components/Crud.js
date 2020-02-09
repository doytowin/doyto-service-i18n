import Page from './Page'
import Util from './Util'

let Crud = function (R, successFunc, errorFunc) {
  let self = this
  self.adding = false
  self.editing = false
  const defaultErrorMessage = '接口调用失败'

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

  let onSuccess = function (response) {
    let data = response.body
    self.p.loading = false
    if (typeof successFunc === 'function') {
      if (successFunc(data)) {
        self.adding = false
        self.editing = false
      }
    } else {
      if (data.success) {
        self.adding = false
        self.editing = false
        self.p.load()
      } else {
        Util.handleFailure(data)
      }
    }
  }

  let onError = function (ret) {
    self.p.loading = false
    if (typeof errorFunc === 'function') {
      errorFunc(ret)
    } else {
      if (ret !== null && typeof ret === 'object') {
        Util.alert('Status[' + ret.status + ']: ' + (ret.data.info || ret.statusText || defaultErrorMessage))
      } else {
        Util.alert(typeof ret === 'string' ? ret : defaultErrorMessage)
      }
    }
  }

  self.cancel = function () {
    self.adding = false
    self.editing = false
  }

  self.add = function () {
    self.record = {}
    self.adding = true
  }

  self.edit = function (record) {
    self.record = simpleCopy(record)
    self.editing = true
  }

  self.view = function (record) {
    self.record = record
  }

  self.save = function (record) {
    if (self.p.loading) {
      return
    }
    self.p.loading = true
    if (!record.id) {
      record.status = 1
      record.valid = 1
        R.save(record).then(onSuccess, onError)
    } else {
        R.update({id: record.id}, record).then(onSuccess, onError)
    }
  }

  self.remove = function (record, message) {
    if (!confirm(message || '确定要删除这条记录吗?')) {
      return
    }
    R.remove({id: record.id}).then(onSuccess, onError)
  }

  self.p = new Page(R.query)// .load()
  self.record = {}
}

export default Crud
