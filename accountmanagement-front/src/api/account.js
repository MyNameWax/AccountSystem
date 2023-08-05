import {get, post} from "@/request/service";

const getList = (searchText) => {
    return get("/manage/list/1/5?platName=" + searchText)
}

const getById = (id) => {
    return get(`/manage/get/${id}`)
}

const updateById = (AccountInfo) => {
    return post(`manage/updateInfo`,AccountInfo)
}

const DeleteAccountById = (id) => {
    return post(`/manage/deleteInfo/${id}`)
}

const insertInfo = (AccountInfo) => {
    return post(`/manage/insertInfo`,AccountInfo)
}

export {
    insertInfo,
    getList,
    getById,
    updateById,
    DeleteAccountById
}
