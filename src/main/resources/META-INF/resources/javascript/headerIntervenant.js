document.addEventListener('DOMContentLoaded', () => {
    fetch('../headers/headerIntervenant.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('header').innerHTML = data;

            // Check if logoutButton exists
            const logoutButton = document.getElementById('logoutButton');
            console.log('Logout Button:', logoutButton);

            if (logoutButton) {
                logoutButton.addEventListener('click', function(event) {
                    event.preventDefault(); // Prevent any default anchor behavior

                    // Clear session or localStorage (adjust as needed)
                    localStorage.removeItem('email');
                    localStorage.removeItem('intervenantId');
                    sessionStorage.removeItem('email');
                    sessionStorage.removeItem('intervenantId');

                    // Redirect to the login page after logging out
                    console.log('Logging out and redirecting...');
                    window.location.replace('../index.html'); // Adjust the path to your login page
                });
            } else {
                console.error("Logout button not found!");  // For debugging
            }
        })
        .catch(error => console.error('Error loading header:', error)); // Handle any errors
});
