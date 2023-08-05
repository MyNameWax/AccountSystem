import axios from "axios";


const axiosInstance = axios.create({
    baseURL:"http://127.0.0.1:9090/api",
    timeout: 10000,
    withCredentials: true
});


axiosInstance.interceptors.request.use(config => {
    return config
})

axiosInstance.interceptors.response.use(response => {
    return response.data
})

// 常用方法
export const get = (url,params) => {
    return axiosInstance.get(url,params)
}
export const post =(url,data) => {
    return axiosInstance.post(url,data)
}
export default axiosInstance


