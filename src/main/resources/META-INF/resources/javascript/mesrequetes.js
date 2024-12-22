// Fetch work requests for the resident with ID 1
const fetchWorkRequests = async () => {
    try {
        // Fetch the work requests
        const response = await fetch('http://localhost:8080/work-requests/resident/1');

        // Check if the response is ok (status 200)
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        // Parse the JSON response
        const data = await response.json();
        console.log("Fetched Work Requests:", data); // Log the fetched data

        // Display the fetched work requests
        displayWorkRequests(data);
    } catch (error) {
        console.error('Error fetching work requests:', error);
    }
};

// Function to display work requests on the page
const displayWorkRequests = (workRequests) => {
    const requestList = document.getElementById('request-list'); // Get the <ul> element

    // Clear the list before displaying new data
    requestList.innerHTML = '';

    // Iterate through each work request and create a list item
    workRequests.forEach(request => {
        const listItem = document.createElement('li');
        listItem.classList.add('work-request-item'); // Add a class for styling

        // Add work request details to the list item
        listItem.innerHTML = `
            <strong>Title:</strong> ${request.title} <br>
            <strong>Description:</strong> ${request.detailedWorkDescription} <br>
            <strong>Neighbourhood:</strong> ${request.neighbourhood} <br>
            <strong>Start Date:</strong> ${request.startDate} <br>
            <strong>Work Type:</strong> ${request.workType} <br>
            <strong>Wished Start Date:</strong> ${request.workWishedStartDate} <br>
            <strong>Candidatures:</strong> ${request.candidatures.length} <br>
        `;

        // If there are candidatures, display their information
        if (request.candidatures.length > 0) {
            const candidaturesList = document.createElement('ul');
            request.candidatures.forEach(candidature => {
                const candidatureItem = document.createElement('li');
                candidatureItem.innerHTML = `<strong>Intervenant:</strong> ${candidature.intervenant.firstName} ${candidature.intervenant.lastName} <br>
                                             <strong>Status:</strong> ${candidature.status}`;
                candidaturesList.appendChild(candidatureItem);
            });
            listItem.appendChild(candidaturesList);
        }

        // Append the list item to the <ul> element
        requestList.appendChild(listItem);
    });
};

// Call the fetchWorkRequests function when the page loads
fetchWorkRequests();


