CREATE TABLE `postagens` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(20) NOT NULL,
  `conteudo` varchar(3000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `usuario` (
  `login` varchar(20) NOT NULL,
  `senha` varchar(20) NOT NULL,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8