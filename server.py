from flask import Flask, jsonify, request, redirect
from flask_uploads import UploadSet, configure_uploads, IMAGES
import os

app = Flask(__name__)

# Configurazione per l'upload delle immagini
uploads = UploadSet('uploads', IMAGES)
app.config['UPLOADED_IMAGES_DEST'] = 'uploads'
configure_uploads(app, uploads)

utente = []
oggetti = []
userDataChanged = False
oggettiDataChanged = False

# Caricamento dei dati degli utenti e degli oggetti
def load_user_data():
    # Implementa il caricamento dei dati degli utenti e degli oggetti da file

# Salvataggio dei dati se sono stati modificati
def save_data_if_changed():
    # Implementa la logica per salvare i dati se sono stati modificati

# Funzione per verificare se un utente esiste già
def is_user_already_exists(username, email):
    # Implementa la logica per verificare se l'utente esiste già

load_user_data()

# Rotte per gli utenti
@app.route('/utente', methods=['GET'])
def get_utente():
    return jsonify(utente)

@app.route('/utente', methods=['POST'])
def create_utente():
    # Implementa la logica per la creazione di un nuovo utente

# Rotte per gli oggetti
@app.route('/oggetti', methods=['GET'])
def get_oggetti():
    return jsonify(oggetti)

@app.route('/oggetti', methods=['POST'])
def create_oggetto():
    # Implementa la logica per la creazione di un nuovo oggetto

# Funzione per verificare le credenziali dell'utente
def is_user_valid(username, password):
    # Implementa la logica per verificare se le credenziali dell'utente sono valide

@app.route('/login', methods=['POST'])
def login():
    # Implementa la logica per il login dell'utente

if __name__ == '__main__':
    app.run(port=3000)