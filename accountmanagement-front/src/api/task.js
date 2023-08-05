import {get, post} from "@/request/service";

const getTask = () => {
    return get("/task/list")
}
const insertTask = (task) => {
    return post("/task/insert",task)
}

const deleteTask = (id) => {
    return post(`/task/delete/${id}`)
}

const changeTaskStatus = (id) => {
    return post(`/task/change/${id}`)
}

export {
    getTask,
    insertTask,
    deleteTask,
    changeTaskStatus
}
