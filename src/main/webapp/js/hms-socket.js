/**
 * socket js
 */
/*if (!user) {
	Tip.message("用户登录过期，请从新登录");
	location.href = contentPath;
}
var socket;
if (!window.WebSocket) {
    window.WebSocket = window.MozWebSocket;
}
// Javascript Websocket Client
if (window.WebSocket) {
    socket = new WebSocket("ws://" + hostAddress + contentPath + "/websocket");
    socket.onmessage = function(event) {
    	Tip.message("Web Socket message" + event.data);
    };
    socket.onopen = function(event) {
    	sendMessageOfWebSocket(JSON.stringify({"directive": "connect", "userId": user.id}));
    	console.log("-------open-------");
    	Tip.message("Web Socket open");
    };
    socket.onclose = function(event) {
    	console.log("-------closed-------");
    	Tip.message("Web Socket closed");
    };
} else {
    alert("Your browser does not support Web Socket.");
}

//Send Websocket data  
function sendMessageOfWebSocket(message) {
    if (!window.WebSocket) { return; }
    if (socket.readyState == WebSocket.OPEN) {
        socket.send(message);
    } else {
        alert("The socket is not open.");
    }
}*/