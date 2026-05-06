class User:
    def __init__(self, id_usuario, estilos_aprendizaje, temas_dominio):
        self.id_usuario = id_usuario
        self.estilos_aprendizaje = estilos_aprendizaje
        self.temas_dominio = temas_dominio

    def get_estilos_aprendizaje(self):
        return self.estilos_aprendizaje

    def get_temasy_dominio(self):
        return self.temas_dominio