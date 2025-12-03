let API = "http://localhost:8080";

// Load items on page load
window.onload = function() {
    loadItems();
};

// Add Item function
function addItem() {
    let name = document.getElementById("itemName").value;
    let quantity = document.getElementById("itemQty").value;

    if (!name || !quantity) {
        alert("Please enter item name and quantity.");
        return;
    }

    fetch(`${API}/items/add`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({ name, quantity: parseInt(quantity) })
    })
    .then(res => res.json())
    .then(data => {
        alert("Item added!");
        loadItems(); // refresh table
    })
    .catch(err => alert("Error adding item: " + err));
}


// Load items into table
function loadItems() {
    fetch(`${API}/items/list`)
        .then(res => res.json())
        .then(items => {
            let tbody = document.querySelector("#itemTable tbody");
            tbody.innerHTML = "";

            items.forEach(item => {
                let row = `
                    <tr>
                        <td>${item.id}</td>
                        <td>${item.name}</td>
                        <td>${item.quantity}</td>
                    </tr>
                `;
                tbody.innerHTML += row;
            });
        })
        .catch(err => console.error(err));
}
