import {get, post} from "@/request/service";

const login = (userInfo) => {
    return post("/account/login",userInfo)
}
const getInfo = () => {
    return get('/account/info');
}

export {
    login,
    getInfo
}
