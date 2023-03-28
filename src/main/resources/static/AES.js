const k='uUXsN6okXYqsh0BB';
function AES_Encrypt(data) {
    // 定义前端Key秘钥-需要注意 跟后端解密秘钥保持一致
    let key=k;
    key = CryptoJS.enc.Utf8.parse(key);
    let encrypted = CryptoJS.AES.encrypt(data, key, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return CryptoJS.enc.Base64.stringify(CryptoJS.enc.Hex.parse(encrypted.ciphertext.toString()));
}
function AES_Decrypt(word) {
    let key=k;
    key = CryptoJS.enc.Utf8.parse(key);
    let decrypt = CryptoJS.AES.decrypt(word, key, {  mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
    let decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
    return decryptedStr;
}
// function test(){
//     let plaintText=document.getElementById("content").value;
//     let cipherText=AES_Encrypt(plaintText);
//     console.log("明文是:"+plaintText);
//     console.log("密文是:"+cipherText);
//     let request=new XMLHttpRequest();
//     request.open("POST","/test");
//     let formData=new FormData();
//     formData.append("cipher",cipherText);
//     request.send(formData);
//     request.onreadystatechange=function(){
//         if(request.readyState==4&&request.status==200){
//             let data=request.responseText;
//             console.log("返回的数据是:"+data);
//             let data2=AES_Decrypt(data);
//             console.log("解密后的数据是："+data2);
//             if(data2.includes(plaintText)){
//                 window.alert("传输成功");
//             }else{
//                 window.alert("传输失败");
//             }
//         }
//     }
// }
//
