document.addEventListener('DOMContentLoaded', () => {
    const residentId = localStorage.getItem('residentId'); // Retrieve residentId from localStorage
    const timeSlotsList = document.getElementById('timeSlotsList'); // List to display selected time slots
    let timeSlots = []; // Array to hold selected time slots

    if (!residentId) {
        alert("Utilisateur non authentifié");
        return;
    }

    // Add time slot to the UI
    document.getElementById('addTimeSlotBtn').addEventListener('click', () => {
        const day = document.getElementById('day').value;
        const startTime = document.getElementById('startTime').value;
        const endTime = document.getElementById('endTime').value;

        if (!day || !startTime || !endTime) {
            alert('Veuillez remplir tous les champs');
            return;
        }

        if (startTime >= endTime) {
            alert("L'heure de début doit être antérieure à l'heure de fin");
            return;
        }

        const timeSlot = { day, startTime, endTime };
        timeSlots.push(timeSlot); // Add new time slot to array
        addTimeSlotToUI(timeSlot); // Display the time slot in the UI

        // Clear the input fields
        document.getElementById('preferencesForm').reset();
    });

    // Save preferences to the backend
    document.getElementById('savePreferencesBtn').addEventListener('click', () => {
        if (timeSlots.length === 0) {
            alert('Ajoutez au moins une plage horaire');
            return;
        }

        // Send the time slots to the backend
        fetch(`/residents/${residentId}/preferences`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(timeSlots)
        })
            .then(response => {
                if (response.ok) {
                    alert('Préférences mises à jour avec succès');
                    timeSlotsList.innerHTML = ''; // Clear the list after saving
                    timeSlots = []; // Reset the time slots array
                } else {
                    response.text().then(text => alert(`Erreur: ${text}`));
                }
            })
            .catch(error => {
                console.error('Erreur:', error);
                alert('Une erreur est survenue');
            });
    });

    // Add time slot to the "Plages Horaires Sélectionnées" list in the UI
    function addTimeSlotToUI(slot) {
        const listItem = document.createElement('li');
        listItem.textContent = `${slot.day}: ${slot.startTime} - ${slot.endTime}`;

        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Supprimer';
        deleteButton.className = 'delete-btn';
        deleteButton.addEventListener('click', () => {
            // Remove from timeSlots array and UI
            timeSlots = timeSlots.filter(ts => !(ts.day === slot.day && ts.startTime === slot.startTime && ts.endTime === slot.endTime));
            listItem.remove();
        });

        listItem.appendChild(deleteButton);
        timeSlotsList.appendChild(listItem);
    }
});