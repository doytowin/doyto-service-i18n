let host = 'http://localhost:10301/';

module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: host + '',
        changeOrigin: true,
        onProxyReq(proxyReq, req, res) {
          console.log(proxyReq.agent.protocol + '//' + proxyReq.connection._host + proxyReq.path)
        }
      },
      '/openapi': {
        target: host,
        changeOrigin: true,
        onProxyReq(proxyReq, req, res) {
          console.log(proxyReq.agent.protocol + '//' + proxyReq.connection._host + proxyReq.path)
        }
      },
      '/static': {
        target: host,
        changeOrigin: true,
        onProxyReq(proxyReq, req, res) {
          console.log(proxyReq.agent.protocol + '//' + proxyReq.connection._host + ':' + proxyReq.connection._port + proxyReq.path)
        }
      },
      '/login': {
        target: host,
        changeOrigin: true,
        onProxyReq(proxyReq, req, res) {
          console.log(proxyReq.agent.protocol + '//' + proxyReq.connection._host + ':' + proxyReq.connection._port + proxyReq.path)
        }
      }
    }
  },

  outputDir: '../target/classes/static'
}
