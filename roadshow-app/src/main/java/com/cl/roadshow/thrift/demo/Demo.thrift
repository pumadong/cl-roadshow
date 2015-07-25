struct Student{
    1:i32               id;
    2:string               name;
}

struct StudentResponse{
    1:list<Student>      studentList;
}

struct StudentResponseRequired{
    1:required list<Student>      studentList;
}

service StudentService
{
    StudentResponse getStudentResponse()

    StudentResponseRequired getStudentResponseRequired()
}