function Parancoe() {
    function Util() {
        this.initDWR = function () {            
            useLoadingMessage();
            dwr.engine.reverseAjax = true;
        };
    }

    this.util = new Util();
}

var parancoe = new Parancoe();

function useLoadingMessage(message) {
  var loadingMessage;
  if (message) loadingMessage = message;
  else loadingMessage = "Loading...";

  dwr.engine.setPreHook(function() {
    var messageZone = $('messageZone');
    if (!messageZone) {
      messageZone = document.createElement('div');
      messageZone.setAttribute('id', 'messageZone');
      messageZone.style.position = "fixed";
      messageZone.style.top = "0px";
      messageZone.style.right = "0px";
      messageZone.style.background = "red";
      messageZone.style.color = "white";
      messageZone.style.fontFamily = "Arial,Helvetica,sans-serif";
      messageZone.style.padding = "4px";
      document.body.appendChild(messageZone);
      var text = document.createTextNode(loadingMessage);
      messageZone.appendChild(text);
    }
    else {
      messageZone.innerHTML = loadingMessage;
      messageZone.style.visibility = 'visible';
    }
  });

  dwr.engine.setPostHook(function() {
    $('messageZone').style.visibility = 'hidden';
  });
}