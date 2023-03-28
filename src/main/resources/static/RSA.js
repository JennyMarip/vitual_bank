//公钥从证书中获取
function RSA_encrypt(plainText,pk){
    let encrypt=new JSEncrypt();
    encrypt.setPublicKey(pk);
    let cipher=encrypt.encrypt(plainText);
    return cipher;
}

// function test(){
//     let plainText=document.getElementById("content").value;
//     console.log("签名前的明文:"+plainText);
//     let sign=RSA_encrypt(plainText,publicKey);
//     console.log("签名后的："+sign);
//     let request=new XMLHttpRequest();
//     request.open("POST","/testRsa");
//     let formData=new FormData();
//     formData.append("sign",sign);
//     request.send(formData);
//     request.onreadystatechange=function(){
//         if(request.status==200&&request.readyState==4){
//             let ack=request.responseText;
//             if(ack.includes("yes")){
//                 window.alert("成功");
//             }else{
//                 window.alert("失败");
//             }
//         }
//     }
// }