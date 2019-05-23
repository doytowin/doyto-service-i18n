var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  url: '"http://localhost:9018/"',
  apiHost: '"/"',
  openapi: '"/openapi/"'
})
