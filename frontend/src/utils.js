import axios from 'axios';
let isDev = import.meta.env.DEV
const serverUrl = isDev ? "https://final-lb-2105412396.us-east-1.elb.amazonaws.com" : "https://final-lb-2105412396.us-east-1.elb.amazonaws.com"
export const axiosInstance = axios.create({
    baseURL : serverUrl + "/home",
    headers: {
        Authorization : `Bearer ${localStorage.getItem("token")}`
    }
})

export const noAuthAxiosInstance = axios.create({
    baseURL : serverUrl + "/home"
})
