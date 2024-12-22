document.addEventListener('DOMContentLoaded', () => {
    fetch('../headers/headerResident.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('header').innerHTML = data;

            const logoutButton = document.getElementById('logoutButton');
            if (logoutButton) {
                logoutButton.addEventListener('click', function(event) {
                    event.preventDefault(); // Prevent default anchor behavior
                    localStorage.removeItem('email');
                    localStorage.removeItem('residentId');
                    sessionStorage.removeItem('email');
                    sessionStorage.removeItem('residentId');
                    window.location.replace('../index.html');
                });
            }

            const residentId = localStorage.getItem('residentId');
            if (residentId) {
                fetchUnreadNotifications(residentId);
            }

            // Notification toggle
            const notificationLink = document.getElementById('notification-link');
            const notificationDropdown = document.getElementById('notification-dropdown');
            const closeBtn = document.getElementById('close-notifications');

            notificationLink.addEventListener('click', function(event) {
                event.preventDefault();
                // Toggle dropdown with animation
                if (notificationDropdown.style.display === 'block') {
                    notificationDropdown.style.display = 'none';
                } else {
                    notificationDropdown.style.display = 'block';
                    showNotifications(residentId);
                }
            });

            closeBtn.addEventListener('click', function() {
                notificationDropdown.style.display = 'none';
            });
        })
        .catch(error => console.error('Error loading header:', error));
});

function fetchUnreadNotifications(residentId) {
    fetch(`/notifications/unread/${residentId}`)
        .then(response => response.json())
        .then(notifications => {
            const notificationCount = notifications.length;
            const notificationCountElement = document.getElementById("notification-count");

            if (notificationCount > 0) {
                notificationCountElement.textContent = notificationCount;
                notificationCountElement.style.display = "inline";
            } else {
                notificationCountElement.style.display = "none";
            }
        })
        .catch(error => console.error("Error fetching notifications:", error));
}

function showNotifications(residentId) {
    fetch(`/notifications/unread/${residentId}`)
        .then(response => response.json())
        .then(notifications => {
            const notificationList = document.getElementById('notification-list');
            notificationList.innerHTML = ''; // Clear previous notifications

            if (notifications.length === 0) {
                notificationList.innerHTML = '<li>No notifications found</li>';
            } else {
                notifications.forEach(notification => {
                    const listItem = document.createElement('li');
                    listItem.classList.add('notification-item');
                    listItem.textContent = notification.message;
                    notificationList.appendChild(listItem);
                });
            }
        })
        .catch(error => console.error("Error fetching notifications:", error));
}
