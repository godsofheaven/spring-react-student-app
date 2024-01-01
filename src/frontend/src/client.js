import fetch from 'unfetch'

const checkStatus = response => {
    if (response.ok) {
        return response;
    }
    // convert non-2xx HTTP responses into errors:
    const error = new Error(response.statusText);
    error.response = response;
    return Promise.reject(error);
}

export const getAllStudents = () =>
    fetch('api/v1/students')
        .then(checkStatus);

export const addNewStudent = student =>
    fetch('api/v1/students', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(student)
    }).then(checkStatus);

export const deleteStudent = student =>
    fetch('api/v1/students/'+student.id, {
        method: 'DELETE'
    }).then(checkStatus);
