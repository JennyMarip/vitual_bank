/****** 获取用户当前状态 ******/
function update(){
    let request=new XMLHttpRequest();
    request.open('GET','/handler0');
    request.send();
    request.onreadystatechange=function(){
        if(request.readyState==4&&request.status==200){
            let pk=request.responseText;
            let privateKey=getRandomString(16);
            let cipher=RSA_encrypt(privateKey,pk);
            let formData=new FormData();
            formData.append("cipher",cipher);
            let request1=new XMLHttpRequest();
            request1.open("POST","/handler1");
            request1.send(formData);
            request1.onreadystatechange=function(){
                if(request1.status==200&&request1.readyState==4){
                    console.log("密文是:"+cipher);
                    let data=request1.responseText;
                    let obj=JSON.parse(data);
                    let name=document.getElementById("username");
                    let balance=document.getElementById("balance");
                    name.innerText=obj.username;
                    let key=privateKey;
                    key = CryptoJS.enc.Utf8.parse(key);
                    let decrypt = CryptoJS.AES.decrypt(obj.balance, key, {  mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
                    let decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
                    balance.innerText=decryptedStr;
                }
            }
        }
    }
}
/*********/
/****** 存款业务 ******/
function doSave(){
    let request0=new XMLHttpRequest();
    request0.open("GET","/doSave0");
    request0.send();
    request0.onreadystatechange=function(){
        if(request0.status==200&&request0.readyState==4){
            let pk=request0.responseText;
            let cardNum=document.getElementById("cardNum0").value;
            let data1=Hybrid_Enc(cardNum,pk);
            let request1=new XMLHttpRequest();
            request1.open("POST","/doSave1");
            request1.send(data1);
            request1.onreadystatechange=function(){
                if(request1.status==200&&request1.readyState==4){
                    let ack1=request1.responseText;
                    if(ack1.includes("yes")){
                        let num=document.getElementById("inputNum").value;
                        if(num<0){
                            window.alert("转账金额不能为负！");
                            window.location.reload();
                        }else{
                            num=num.toString();
                            let data2=Hybrid_Enc(num,pk);
                            let request2=new XMLHttpRequest();
                            request2.open("POST","/doSave2");
                            request2.send(data2);
                            request2.onreadystatechange=function(){
                                if(request2.status==200&&request2.readyState==4){
                                    let ack2=request2.responseText;
                                    if(ack2.includes("yes")){
                                        window.alert("存款成功！");
                                        window.location.reload();
                                    }else if(ack2.includes("CardError")){
                                        window.alert("输入的银行卡号错误！");
                                        window.location.reload();
                                    }else{
                                        window.alert("发生内部错误！");
                                        window.location.reload();
                                    }
                                }
                            }
                        }
                    }else{
                        window.alert("发生内部错误！");
                        window.location.reload();
                    }
                }
            }
        }
    }
}
/*********/
/****** 取款业务 ******/
function doWithdraw(){
    let request0=new XMLHttpRequest();
    request0.open("GET","/doWithdraw0");
    request0.send();
    request0.onreadystatechange=function(){
        if(request0.status==200&&request0.readyState==4){
            let pk=request0.responseText;
            let cardNum=document.getElementById("cardNum").value;
            let num=document.getElementById("num").value;
            if(num<0){
                window.alert("取款金额不能为负！");
                window.location.reload();
            }
            num=num.toString();
            let data1=Hybrid_Enc(cardNum,pk);
            let request1=new XMLHttpRequest();
            request1.open("POST","/doWithdraw1");
            request1.send(data1);
            request1.onreadystatechange=function() {
                if (request1.status == 200 && request1.readyState == 4) {
                    let ack1 = request1.responseText;
                    console.log("ack1是" + ack1);
                    if (ack1.includes("yes")) {
                        num = num.toString();
                        let data2 = Hybrid_Enc(num, pk);
                        let request2 = new XMLHttpRequest();
                        request2.open("POST", "/doWithdraw2");
                        request2.send(data2);
                        request2.onreadystatechange = function () {
                            if(request2.status==200&&request2.readyState==4) {
                                let ack2 = request2.responseText;
                                console.log("ack2是" + ack2);
                                if (ack2.includes("yes")) {
                                    window.alert("取款成功！");
                                    window.location.reload();
                                } else if (ack2.includes("cardNumError")) {
                                    window.alert("银行卡号错误！");
                                    window.location.reload();
                                } else if (ack2.includes("numError")) {
                                    window.alert("余额不足！");
                                    window.location.reload();
                                } else {
                                    window.alert("发生内部错误！");
                                    window.location.reload();
                                }
                            }
                        }
                    } else {
                        window.alert("发生内部错误！");
                        window.location.reload();
                    }
                }
            }
        }
    }
}
/*********/

/****** 转账业务 ******/
function transfer(){
    let cardNum=document.getElementById("cardNum2").value;
    let target=document.getElementById("target").value;
    let num=document.getElementById("tranAmount").value;
    if(num<0){
        window.alert("转账金额不能为负！");
        window.location.reload();
    }else{
        let request0=new XMLHttpRequest();
        request0.open("GET","/doTrans0");
        request0.send();
        request0.onreadystatechange=function(){
            if(request0.status==200&&request0.readyState==4){
                let pk=request0.responseText;
                let data1=Hybrid_Enc(cardNum,pk);
                let request1=new XMLHttpRequest();
                request1.open("POST","/doTrans1");
                request1.send(data1);
                request1.onreadystatechange=function(){
                    if(request1.status==200&&request1.readyState==4){
                        let ack1=request1.responseText;
                        if(ack1.includes("yes")){
                            let data2=Hybrid_Enc(target,pk);
                            let request2=new XMLHttpRequest();
                            request2.open("POST","/doTrans2");
                            request2.send(data2);
                            request2.onreadystatechange=function(){
                                if(request2.status==200&&request2.readyState==4){
                                    let ack2=request2.responseText;
                                    if(ack2.includes("yes")){
                                        num=num.toString();
                                        let data3=Hybrid_Enc(num,pk);
                                        let request3=new XMLHttpRequest();
                                        request3.open("POST","/doTrans3");
                                        request3.send(data3);
                                        request3.onreadystatechange=function(){
                                            if(request3.status==200&&request3.readyState==4){
                                                let ack3=request3.responseText;
                                                if(ack3.includes("yes")){
                                                    window.alert("转账成功！");
                                                    window.location.reload();
                                                }else if(ack3.includes("numError")){
                                                    window.alert("您的余额不足！");
                                                    window.location.reload();
                                                }else{
                                                    window.alert("发生内部错误！");
                                                    window.location.reload();
                                                }
                                            }
                                        }
                                    }else if(ack2.includes("targetError")){
                                        window.alert("目的账户不存在！");
                                        window.location.reload();
                                    }else{
                                        window.alert("发生内部错误！");
                                        window.location.reload();
                                    }
                                }
                            }
                        }else if(ack1.includes("cardNumError")){
                            window.alert("输入的银行卡号错误！");
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
/*********/
