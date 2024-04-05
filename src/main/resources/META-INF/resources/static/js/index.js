document.getElementById('person-form').addEventListener('submit', function (event) {
    event.preventDefault(); // Evita que o formulário seja enviado diretamente

    // Obtém os dados do formulário
    const formData = new FormData(this);

    // Converte os dados do formulário para um objeto JSON
    const jsonData = {};
    formData.forEach((value, key) => {
        jsonData[key] = value;
    });

    fetch('/person', {
        method: 'POST', headers: {
            'Content-Type': 'application/json'
        }, body: JSON.stringify(jsonData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao enviar os dados');
            }
            return response.json();
        })
        .then(data => {
            console.log('Dados enviados com sucesso:', data);
            // Aqui você pode manipular a resposta do servidor, se necessário
        })
        .catch(error => {
            console.error('Erro:', error);
        });
})

