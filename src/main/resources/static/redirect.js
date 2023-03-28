function toPersonal(){
    let request=new XMLHttpRequest();
    request.open('GET','/personal');
    request.send();
    request.onreadystatechange=function(){
        if(request.readyState ==4&&request.status==200){
            window.open('/personal');
        }
    }
}
function toSave(){
    let request=new XMLHttpRequest();
    request.open('GET','/toSave');
    request.send();
    request.onreadystatechange=function(){
        if(request.readyState ==4&&request.status==200){
            window.open('/toSave');
        }
    }
}
function toWithdraw(){
    let request=new XMLHttpRequest();
    request.open('GET','/toWithdraw');
    request.send();
    request.onreadystatechange=function(){
        if(request.readyState ==4&&request.status==200){
            window.open('/toWithdraw');
        }
    }
}
function toTrans(){
    let request=new XMLHttpRequest();
    request.open('GET','/toTrans');
    request.send();
    request.onreadystatechange=function(){
        if(request.readyState ==4&&request.status==200){
            window.open('/toTrans');
        }
    }
}
function toService(){
    let request0=new XMLHttpRequest();
    request0.open("GET","/toService");
    request0.send();
    request0.onreadystatechange=function(){
        if(request0.status==200&&request0.readyState==4){
            let ack=request0.responseText;
            if(ack.includes("yes")){
                window.open("/Service")
            }else{
                window.alert("本账号尚未开户，请创建账户！");
            }
        }
    }
}
