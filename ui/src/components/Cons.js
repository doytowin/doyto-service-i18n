
export default {
  authUrl: process.env.VUE_APP_AUTH_URL,
  host: (uri) => {
    return process.env.VUE_APP_API_HOST + uri
  },
  api: (uri) => {
    return process.env.VUE_APP_API_HOST + 'api/' + uri
  },
  openApi: (uri) => {
    return process.env.VUE_APP_API_HOST + 'openapi/' + uri
  }
}
