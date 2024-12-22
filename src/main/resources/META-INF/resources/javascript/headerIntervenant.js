// headerIntervenant.js
document.addEventListener('DOMContentLoaded', () => {
    // Load the header HTML
    fetch('../headers/headerIntervenant.html')
        .then(response => response.text())
        .then(data => {
            // Inject the header into the element with id="header"
            document.getElementById('header').innerHTML = data;

            // Retrieve the logout button from the newly injected header
            const logoutButton = document.getElementById('logoutButton');
            console.log('Logout Button:', logoutButton);

            if (logoutButton) {
                logoutButton.addEventListener('click', (event) => {
                    event.preventDefault(); // Prevent navigation

                    // Remove intervenant credentials from storage
                    localStorage.removeItem('email');
                    localStorage.removeItem('intervenantId');
                    sessionStorage.removeItem('email');
                    sessionStorage.removeItem('intervenantId');

                    // Redirect to login or homepage
                    console.log('Logging out and redirecting...');
                    window.location.replace('../DefaultPage.html');
                });
            } else {
                console.error("Logout button not found!");
            }
        })
        .catch(error => console.error('Error loading header:', error));
});
