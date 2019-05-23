import Vue from 'vue'
import Util from './Util'

/**
 * 分页的处理类
 */
let Page = function (queryFunc) {
  'use strict'

  let self = this
  let page = 0
  let size = 10
  let pages = 0
  let total = 0
  let qo
  this.page = 0
  this.size = 10
  this.sizes = [5, 10, 20, 30, 50, 100]
  this.pages = 0
  this.total = 0
  this.from = 0
  this.to = 0

  function SuccessCallback (rsp) {
    let p = this
    if (rsp.success) {
      let data = rsp.data
      if (typeof data.total === 'number') {
        total = data.total
        let newPages = Math.ceil(total / size)
        if (pages !== newPages) {
          pages = newPages
          if (page > newPages) { // 当前页号page大于总页数时，是没有数据的，需要修正page然后重新加载
            page = Math.min(newPages, 1)
            p.load()
            return
          }
        }
        if (!page && pages) {
          page = 1
        }
        p.page = page
        p.size = size
        p.pages = pages
        p.total = total
        p.from = Math.max((page - 1) * size + 1, 0)
        p.to = Math.min(page * size, total)
      }
      p.list = data.list
    } else {
      Util.handleFailure(rsp)
    }
  }

  this.loading = false
  this.q = {}
  this.isQueryChanged = function () {
    return !Vue.util.equals(this.q, qo)
  }
  this.load = function () {
    let q = self.q
    q.pageNumber = (page || 1) - 1
    q.pageSize = size
    qo = JSON.parse(JSON.stringify(q))
    self.loading = true
    queryFunc(q).then(response => {
      SuccessCallback.call(self, response.body)
      self.loading = false
    }, response => {
      // console.log(response)
      // 出错时回退page/size
      page = self.page || page
      size = self.size || size

      self.loading = false
      Util.handleFailure(response)
    })
    return this
  }
  this.first = function () {
    if (page > 1) {
      page = 1
      self.load()
    }
  }
  this.last = function () {
    if (page !== pages) {
      page = pages
      self.load()
    }
  }
  this.prev = function () {
    if (page > 1) {
      page--
      self.load()
    }
  }
  this.next = function () {
    if (page < pages) {
      page++
      self.load()
    }
  }
  this.resize = function (pageSize) { // 设置每页显示条数
    size = pageSize
    page = Math.min(page, Math.ceil(total / size))// 当前页数p不能大于总页数
    page = Math.max(page, 1)
    self.load()
  }
  this.goto = function (goto) {
    goto = parseInt(goto) || 1
    if (page !== goto) {
      page = goto
      self.load()
    }
  }
  this.reset = function () {
    this.q = {}
    page = 1
    self.load()
  }
  this.list = []
}

export default Page
