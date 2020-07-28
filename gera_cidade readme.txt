O script Gera-Cidades possui:


list_of_cities => Contem o nome das 49 cidades 

api_key => Contem a chave de acesso do google maps

Os "for's" das linhas seguintes fazem a requisição da distancia entre uma cidade e outra com a função "distance_matrix(cidade1, cidade2)", a mesma retorna um json com informações como a distância em km e a duração.

Após todas as requisições com as informações de distância e durancao, que estão armazenadas na lista "linhas", os "for's" seguintes são para salvar as informações em arquivo txt. 

 