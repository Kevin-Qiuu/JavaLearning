$(document).ajaxError(function (event, xhr, options, exc) {
    if (xhr.status === 400) {
        alert("参数校验失败");
    } else if (xhr.status === 401) {
        //已经被拦截器拦截了，未登录
        location.href = "blog_login.html";
    }
});

$(document).ajaxSend(function (e, xhr, opt) {
    // console.log("设置 UserToken Http 报文头")
    let userToken = localStorage.getItem("userToken");
    xhr.setRequestHeader("userToken", userToken);
})

function logout(){
    // 删除 cookie
    localStorage.removeItem("userToken");
    alert("用户已注销，即将跳转到登录页!");
    location.href = "blog_login.html";
}