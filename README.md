# drzewo

Son dos aplicaciones independientes. Una para administración de álbumes y otra para visualizarlos. 

## Administración

Está en la carpeta admin. Es una aplicación node, por lo que para instalarla es necesario ejecutar:

```
cd admin
npm install 
npm start
```


Es una aplicación para mostra distintos álbumes de fotos cargados previamente.

## ToDo list

- [ ] get /albums: devolverá todos los álbumes cargados para la pantalla de índice
- [ ] get /albums?nombre=nombre: seleccionará únicamente un álbum cuando se haga click en el nombre
- [ ] rotar imágenes en modo presentación
- [ ] integrar passport
- [ ] definir pantalla para gestionar el CRUD de álbumes
- [ ] internacionalización de textos
- [ ] hacer jail para la carpeta de fotos y no navegar fuera de ahí
- [ ] i18n http://markocen.github.io/blog/i18n-for-node-application.html
