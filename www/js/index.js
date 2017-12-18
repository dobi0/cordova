var record_camera = false;

function record_Start(){
    var record_switch = false;
	var request = new XMLHttpRequest();
    var url = 'http://192.168.1.8:8000/isp2/record/movie'
	request.open("POST", url);
    request.setRequestHeader('Access-Control-Allow-Origin','*');
	request.addEventListener("load", (event) => {
         alert('1');
        this.record_switch = true;
    	console.log(event.target.status); // => 200
    	console.log(event.target.responseText); // => "{...}"
	});
	request.addEventListener("error", () => {
        //alert('Network Error');
    });
     alert('2');
     request.send("a");
   // if(this.record_switch){
         navigator.device.capture.captureVideo(captureSuccess, captureError, {limit: 2});
         this.record_switch = false;
         this.record_camera = true;
   // } 
}

function record_Stop(){
    if(this.record_camera){
       var url = 'http://192.168.1.8:8000/isp2/record/movie'
	   request.open("POST", url);
       request.setRequestHeader('Access-Control-Allow-Origin','*');
	       request.addEventListener("load", (event) => {
                alert('Server Shutdown');
           });
           request.addEventListener("error", () => {
            alert('Network Error');
           });
         request.send("a")
        this.record_camera = false;
       }
    else{
        alert('Server is not runnning');
    }
}
function captureSuccess(mediaFiles) {
    //var i, len;
    //for (i = 0, len = mediaFiles.length; i < len; i += 1) {
    //    uploadFile(mediaFiles[i]);
    //}
}

function uploadFile(mediaFile) {
    var ft = new FileTransfer(),
        path = mediaFile.fullPath,
        name = mediaFile.name;

    ft.upload(path,
        "http://my.domain.com/upload.php",
        function(result) {
            console.log('アップロード成功: ' + result.responseCode);
            console.log(result.bytesSent + ' バイト送信');
        },
        function(error) {
            console.log('ファイルのアップロードに失敗 ' + path + ': ' + error.code);
        },
        { fileName: name });
}
// エラー発生時の処理
//
function captureError(error) {
    var msg = 'キャプチャー中にエラーが発生しました: ' + error.code;
    navigator.notification.alert(msg, null, 'エラー');
}

window.addEventListener("load", function() {
	var button = document.getElementById("record_start");
	button.addEventListener("click", record_Start, false)/* */;
    var button2 = document.getElementById("record_stop");
	button2.addEventListener("click", record_Stop, false)/* */;
}, false);


