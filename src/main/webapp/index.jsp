<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello World !</h1>
在还没有搭建View层时对Controller层进行测试(这里以按条件查询为例,如果是CUD的url则访问后会对数据库数据进行CUD并在页面上展示0/1):<br>
直接在地址栏键入 $ { pageContext.request.contextPath }/user/selectUserPage?userName=&userSex=<br>
<br>
观察数据时(尤其是观察集合数据),建议通过 'F12->Network->对应请求->Preview' 查看

</body>
</html>
