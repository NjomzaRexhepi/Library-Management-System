import helpers from './helpers.js';

window.addEventListener('load', () => {
    //When the video frame is clicked. This will enable picture-in-picture
    document.getElementById('local').addEventListener('click', () => {
        if (!document.pictureInPictureElement) {
            document.getElementById('local').requestPictureInPicture()
                .catch(error => {
                    console.error(error);
                });
        } else {
            document.exitPictureInPicture()
                .catch(error => {
                    console.error(error);
                });
        }
    });

    //When the 'Create room" is button is clicked
    document.getElementById('create-room').addEventListener('click', (e) => {
        e.preventDefault();

        let roomName = document.querySelector('#room-name').value;
        let yourName = document.querySelector('#your-name').value;

        if (roomName && yourName) {
            document.querySelector('#err-msg').innerText = "";
            sessionStorage.setItem('username', yourName);

            //create room link
            let roomLink = `${ location.origin }?room=${ roomName.trim().replace( ' ', '_' ) }_${ helpers.generateRandomString() }`;

            //show message with link to room
            document.querySelector('#room-created').innerHTML = `Room successfully created. Click <a href='${ roomLink }'>here</a> to enter room. 
                Share the room link with your partners.`;

            //empty the values
            document.querySelector('#room-name').value = '';
            document.querySelector('#your-name').value = '';
        } else {
            document.querySelector('#err-msg').innerText = "All fields are required";
        }
    });

    //When the 'Join room' button is clicked
    document.getElementById('join-room-btn').addEventListener('click', (e) => {
        e.preventDefault();
        const url = document.querySelector('#join-room').value;
        if (url) {
            window.open(url);
        } else {
            document.querySelector('#err-msg').innerText = "Please input your URL";
        }
    });

    //When the 'Enter room' button is clicked.
    document.getElementById('enter-room').addEventListener('click', (e) => {
        e.preventDefault();

        let name = document.querySelector('#username').value;

        if (name) {
            document.querySelector('#err-msg-username').innerText = "";

            sessionStorage.setItem('username', name);

            location.reload();
        } else {
            document.querySelector('#err-msg-username').innerText = "Please input your name";
        }
    });


    document.addEventListener('click', (e) => {
        if (e.target && e.target.classList.contains('expand-remote-video')) {
            helpers.maximiseStream(e);
        } else if (e.target && e.target.classList.contains('mute-remote-mic')) {
            helpers.singleStreamToggleMute(e);
        }
    });

});