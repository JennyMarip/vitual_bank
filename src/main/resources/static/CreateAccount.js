function toCreateAccount(){
    let request0=new XMLHttpRequest();
    request0.open("GET","/toCreateAccount");
    request0.send();
    request0.onreadystatechange=function(){
        if(request0.status==200&&request0.readyState==4){
            window.open("/toCreateAccount");
        }
    }
}
function CreateAccount(){
    let username=document.getElementById("username").value;
    let password=document.getElementById("password").value;
    let cardNum0=document.getElementById("cardNumA").value;
    let cardNum1=document.getElementById("cardNumB").value;
    if(username==null||password==null){
        window.alert("用户名或密码为空！");
        window.location.reload();
    }
    if(cardNum0==null||cardNum1==null){
        window.alert("银行卡号为空！");
        window.location.reload();
    }
    if(cardNum0!==cardNum1){
        window.alert("两次输入的银行卡号不同！");
        window.location.reload();
    }else{
        let request0=new XMLHttpRequest();
        request0.open("GET","/CreateAccount0");
        request0.send();
        request0.onreadystatechange=function(){
            if(request0.status==200&&request0.readyState==4){
                let pk=request0.responseText;
                let data1=Hybrid_Enc(cardNum0,pk);
                let hashcode=sha1(password);
                data1.append("username",username);
                data1.append("hashcode",hashcode);
                let request2=new XMLHttpRequest();
                request2.open("POST","/CreateAccount1");
                request2.send(data1);
                request2.onreadystatechange=function(){
                    if(request2.status==200&&request2.readyState==4){
                        let ack=request2.responseText;
                        if(ack.includes("yes")){
                            window.alert("创建账户成功！");
                            window.location.replace("/welcome-to-vBank")
                        }else if(ack.includes("nameError")){
                            window.alert("输入的账号错误！");
                            window.location.reload();
                        }else if(ack.includes("passError")){
                            window.alert("输入的密码错误！");
                            window.location.reload();
                        }else if(ack.includes("cardError")){
                            window.alert("该银行卡号已经创建过账户！");
                            window.location.reload();
                        }else{
                            window.alert("发生内部错误！");
                            window.location.reload();
                        }
                    }
                }
            }
        }
    }
}