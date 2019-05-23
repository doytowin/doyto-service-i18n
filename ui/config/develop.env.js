var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

let apiHost = 'http://api.doyto.local/'

module.exports = merge(prodEnv, {
  url: '"http://i18n.doyto.local/"',
  apiHost: '"' + apiHost + '"',
  openapi: '"' + apiHost + 'openapi/"'
})
