import Vue from 'vue'
import Util from './Util'

/**
 * 分页的处理类
 */
let Page = function (queryFunc) {
  'use strict'

  let self = this
  let page = 0
  let limit = 10
  let pages = 0
  let total = 0
  let qo

  function SuccessCallback (data) {
    let p = this
    if (data.success) {
      if (typeof data.total === 'number') {
        total = data.total
        let newPages = Math.ceil(total / limit)
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
        p.limit = limit
        p.pages = pages
        p.total = total
        p.from = Math.max((page - 1) * limit + 1, 0)
        p.to = Math.min(page * limit, total)
      }
      p.list = data.list
    } else {
      Util.handleFailure(data)
    }
  }

  this.loading = false
  this.q = {}
  this.isQueryChanged = function () {
    return !Vue.util.equals(this.q, qo)
  }
  this.load = function () {
    let q = self.q
    q.page = page || 1
    q.limit = limit
    qo = JSON.parse(JSON.stringify(q))
    self.loading = true
    queryFunc(q).then(response => {
      SuccessCallback.call(self, response.body)
      self.loading = false
    }, response => {
      console.log(response)
      self.loading = false
      Util.handleFailure(response.body)
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
  this.size = function (size) { // 设置每页显示条数
    limit = size
    page = Math.min(page, Math.ceil(total / limit))// 当前页数p不能大于总页数
    page = Math.max(page, 1)
    self.load()
  }
  this.goto = function (goto) {
    page = goto
    self.load()
  }
  this.reset = function () {
    this.q = {}
    page = 1
    self.load()
  }
  this.list = []
}

export default Page
