
let proxy = (host) => {
  return {
    target: host || 'http://localhost:10301/',
    changeOrigin: true,
    onProxyReq(proxyReq) {
      let port = proxyReq.connection._port ? ':' + proxyReq.connection._port : '';
      console.log(proxyReq.agent.protocol + '//' + proxyReq.connection._host + port + proxyReq.path)
    }
  }
};

module.exports = {
  devServer: {
    proxy: {
      '/session': proxy(),
      '/login': proxy(),
      '/logout': proxy(),
      '/api': proxy(),
      '/openapi': proxy(),
      '/static': proxy()
    }
  },

  publicPath: './',
  outputDir: '../target/classes/static'
}
