/****** 处理登录请求 ******/
function doLogin(){
    let username=document.getElementById("username").value;
    let password=document.getElementById("password").value;
    if(username==null||password==null){
        window.alert("用户名或密码为空！");
        window.location.reload();
    }
    let request0=new XMLHttpRequest();
    request0.open("POST","/authentication0");
    let formData=new FormData();
    formData.append("username",username);
    request0.send(formData);
    request0.onreadystatechange=function(){
        if(request0.status==200&&request0.readyState==4){
            let ack=request0.responseText;
            if(ack.includes("error")){
                window.alert("用户名或密码错误，请重试！");
                window.location.reload();
            }else {
                let inquiry=ack;
                let hashcode=sha1(password);
                let combine=hashcode+inquiry;
                let result=sha1(combine);
                let request1=new XMLHttpRequest();
                request1.open("POST","/authentication1");
                let formData2=new FormData();
                formData2.append("result",result);
                request1.send(formData2);
                request1.onreadystatechange=function(){
                    if(request1.status==200&&request1.readyState==4){
                        let answer=request1.responseText;
                        if(answer.includes("yes")){
                            window.alert("登录成功");
                            window.location.replace("/welcome-to-vBank");
                        }else{
                            window.alert("用户名或密码错误，请重试！");
                            window.location.reload();
                        }
                    }
                }
            }
        }
    }
}
/********

/****** 处理注册 ******/
function SignIn(){
    window.location.replace('/signIn');
}
function doSignIn(){
    let username=document.getElementById("username").value;
    let password=document.getElementById("password").value;
    if(username.length==0||password.length==0){
        window.alert("用户名或密码为空！");
        window.location.reload();
    }
    let hashcode=sha1(password);
    let request0=new XMLHttpRequest();
    request0.open('POST','/doSignIn0');
    let formData=new FormData();
    formData.append('username',username);
    formData.append('hashcode',hashcode);
    request0.send(formData);
    request0.onreadystatechange=function(){
        if(request0.readyState==4&&request0.status==200){
            let ack1=request0.responseText;
            if(ack1.includes("nameError")){
                window.alert("该用户名已注册过！");
                window.location.reload();
            }else if(ack1.includes("passError")){
                window.alert("该密码已注册过！");
                window.location.reload();
            }else{
                let request1=new XMLHttpRequest();
                request1.open("GET","/doSignIn1");
                request1.send();
                request1.onreadystatechange=function(){
                    if(request1.status==200&&request1.readyState==4){
                        window.location.replace("/welcome-to-vBank");
                    }
                }
            }
        }
    }
}
/*********/

/*** 退出登录 ***/
function outLog(){
    let request=new XMLHttpRequest();
    request.open("POST","/login");
    request.send();
    window.alert("退出登录?")
    request.onreadystatechange=function(){
        if(request.readyState==4&&request.status==200){
            window.location.replace("/login");
        }
    }
}
