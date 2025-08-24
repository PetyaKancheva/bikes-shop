// $(document).ready(function(){
//     alert("alert petya");
// });
$('#get-all-button').click(fetchAllComments);

$('#add-comment-button').click(submitComment);

$('#single-comment-button').click(fetchSingleComment);

$('#delete-comment-button').click(deleteComment);

let errorMsg = 'Must be populated';
let errorP = document.createElement('p');
errorP.textContent = errorMsg;
errorP.className = " alert alert-danger input-group-sm mb-3";

async function fetchAllComments() {
    const url = '/api/comments/fetch';

    try {
        const response = await fetch(url);
        if (!response.ok) {
            alert("Error status:" + response.status);
        }

        const json = response.json();

        json.forEach((c, comments) => {
            if (comments % 3 === 0) {
                $('.data-container').append('<div class="row d-flex justify-content-around mt-4">');
            }
            let commentCard =

                '<div class="card m-3" style="width: 18rem">' +
                '<div className="card-body">' +
                '<h5 class="card-title">' + c.title + '</h5>' +
                '<h6 class="card-subtitle mb-2 text-body-secondary">ID: ' + c.id + ' By: ' + c.user_name + '</h6>' +
                '<p class="card-text ">' + c.body + '</p>'
                + '</div>' + '</div>' + '</div>';
            $('.data-container .row:last-child').append(commentCard);
        });
    } catch (error) {
        alert("Error" + error);
    }
}


async function fetchSingleComment() {

    let singleCommentID = $('#single-comment-input').val();
    const url = `/api/comment/${singleCommentID}`;

    if (singleCommentID > 0) {
        try {
            const response = await fetch(url);
            if (!response.ok) {
                const data = response.json();
                alert("Error status: " + data);
            } else {
                const json = response.json();
                json.then((c) => {
                    let commentCard =
                        // '<div class="row d-flex">' +
                        '<div class="card m-3" style="width: 18rem">' +
                        '<div className="card-body">' +
                        '<h5 class="card-title">' + c.title + '</h5>' +
                        '<h6 class="card-subtitle mb-2 text-body-secondary">ID: ' + c.id + ' By: ' + c.user_name + '</h6>' +
                        '<p class="card-text ">' + c.body + '</p>'
                        + '</div>' + '</div>' + '</div>';
                    $('#single-comment-container').after(commentCard);
                });
            }

        } catch (error) {
            alert("Error " + error);
        }
    } else {
        $('#single-comment-container').after(errorP);
    }


}


async function deleteComment() {
    let deleteID = $('#delete-comment-input').val();
    const uri = `/api/comment/delete/${deleteID}`;

    if (deleteID.length > 0) {
        try {
            const response = await fetch(uri);
            if (!response.ok) {
                alert("Error status: " + response.body);
            } else {
                const json = response.json();
            }

        } catch (error) {
            alert("Error: " + error);
        }
    } else {
        $('#delete-comment-container').after(errorP);
    }
}

// post complete form. do by each field to catch errors

function submitComment() {
    let errFormMsg = '';

    if ($('#user_name').val().length < 3 || $('#user_name').val().length > 15) {
        errFormMsg = 'Name must be between 3 and 15 characters. ';
    }
    if ($('#title').val().length < 3 || $('#title').val().length > 15) {
        errFormMsg += 'Title must be between 3 and 15 characters.';
    }
    if ($('#body').val().length < 3) {
        errFormMsg += 'Comment must be more than 3 characters. ';
    }

    if (errFormMsg.length > 0) {
        let errorP = document.createElement('p');
        errorP.innerText = errFormMsg;
        errorP.className = " alert alert-danger input-group-sm mb-3";
        $('#new-comment-form').after(errorP);
        return;
    }
    try {
        $('#new-comment-form').submit();
    } catch (e) {
        alert("Error status:" + e);
    }


}
