
export default {
  authUrl: process.env.VUE_APP_AUTH_URL,
  apiHost: process.env.VUE_APP_API_HOST,
  openapi: process.env.VUE_APP_API_HOST + 'openapi/',
  api: (uri) => {
    return process.env.VUE_APP_API_HOST + uri
  },
  openApi: (uri) => {
    return process.env.VUE_APP_API_HOST + 'openapi/' + uri
  }
}
