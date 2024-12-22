document.addEventListener('DOMContentLoaded', () => {
    // Load the header content
    fetch('../headers/headerResident.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('header').innerHTML = data;

            // Add event listener for logout
            const logoutButton = document.getElementById('logoutButton');
            if (logoutButton) {
                logoutButton.addEventListener('click', function(event) {
                    event.preventDefault(); // Prevent default anchor behavior

                    // Clear session or localStorage (adjust as needed)
                    localStorage.removeItem('email');
                    localStorage.removeItem('residentId');
                    sessionStorage.removeItem('email');
                    sessionStorage.removeItem('residentId');

                    // Redirect to the login page after logging out
                    console.log('Logging out and redirecting...');
                    window.location.replace('../index.html'); // Adjust the path to your login page
                });
            } else {
                console.error("Logout button not found!");  // For debugging
            }

            // Fetch unread notifications if the user is logged in
            const residentId = localStorage.getItem('residentId');
            if (residentId) {
                fetchUnreadNotifications(residentId);
            }

            // Notification icon click event to toggle notifications dropdown
            const notificationLink = document.getElementById('notification-link');
            if (notificationLink) {
                notificationLink.addEventListener('click', function(event) {
                    event.preventDefault();
                    const notificationDropdown = document.getElementById('notification-dropdown');
                    notificationDropdown.style.display = notificationDropdown.style.display === 'none' ? 'block' : 'none';
                    if (notificationDropdown.style.display === 'block') {
                        showNotifications(residentId);
                    }
                });
            }
        })
        .catch(error => console.error('Error loading header:', error));
});


// Fetch unread notifications count and show in dropdown
function fetchUnreadNotifications(residentId) {
    fetch(`/notifications/unread/${residentId}`)
        .then(response => response.json())
        .then(notifications => {
            const notificationCount = notifications.length;
            const notificationCountElement = document.getElementById("notification-count");

            // Update notification count
            if (notificationCount > 0) {
                notificationCountElement.textContent = notificationCount;
                notificationCountElement.style.display = "inline";
            } else {
                notificationCountElement.style.display = "none";
            }
        })
        .catch(error => console.error("Error fetching notifications:", error));
}

// Show notifications in the dropdown
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
                    listItem.textContent = notification.message;
                    notificationList.appendChild(listItem);
                });
            }
        })
        .catch(error => console.error("Error fetching notifications:", error));
}
