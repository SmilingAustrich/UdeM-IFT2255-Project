const fetchWorkRequests = async () => {
    try {
        // Get residentId from localStorage
        const residentId = localStorage.getItem('residentId');
        if (!residentId) {
            throw new Error("Resident ID not found in localStorage");
        }

        // Fetch the work requests for the logged-in resident
        const response = await fetch(`http://localhost:8080/work-requests/resident/${residentId}`);

        // Check if the response is ok (status 200)
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        // Parse the JSON response
        const data = await response.json();

        // Check if data is valid and not empty
        if (Array.isArray(data) && data.length > 0) {
            displayWorkRequests(data); // Display the work requests if found
        } else {
            document.getElementById('request-list').innerHTML = '<li>Aucune requête trouvée.</li>';
        }
    } catch (error) {
        console.error('Error fetching work requests:', error);
        document.getElementById('request-list').innerHTML = `<li>${error.message}</li>`;
    }
};

// Function to display work requests on the page
const displayWorkRequests = (workRequests) => {
    const requestList = document.getElementById('request-list');
    requestList.innerHTML = ''; // Clear any previous data

    // Iterate through the work requests and create list items
    workRequests.forEach(request => {
        const listItem = document.createElement('li');
        listItem.classList.add('work-request-item'); // Add a class for styling

        // Ensure that the properties exist before accessing them
        const workTitle = request.workTitle || 'No Title';
        const detailedWorkDescription = request.detailedWorkDescription || 'No Description';
        const neighbourhood = request.neighbourhood || 'No Neighbourhood';
        const startDate = request.startDate ? new Date(request.startDate).toLocaleDateString() : 'No Start Date';
        const workType = request.workType || 'No Work Type';
        const workWishedStartDate = request.workWishedStartDate ? new Date(request.workWishedStartDate).toLocaleDateString() : 'No Wished Start Date';
        const candidaturesCount = request.candidatures ? request.candidatures.length : 0;

        // Add work request details to the list item
        listItem.innerHTML = `
            <strong>Title:</strong> ${workTitle} <br>
            <strong>Description:</strong> ${detailedWorkDescription} <br>
            <strong>Neighbourhood:</strong> ${neighbourhood} <br>
            <strong>Start Date:</strong> ${startDate} <br>
            <strong>Work Type:</strong> ${workType} <br>
            <strong>Wished Start Date:</strong> ${workWishedStartDate} <br>
            <strong>Candidatures:</strong> ${candidaturesCount} <br>
        `;

        // Append the list item to the <ul> element
        requestList.appendChild(listItem);
    });
};

// Call the fetchWorkRequests function when the page loads
fetchWorkRequests();
