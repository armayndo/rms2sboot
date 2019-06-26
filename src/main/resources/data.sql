-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.37-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping data for table spring_security_custom_user_demo.employee: ~4 rows (approximately)
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`id`, `first_name`, `last_name`, `email`) VALUES
	(1, 'roma', 'dhona', 'armayndo1@yahoo.com'),
	(2, 'Alya Nofilia', 'armayndo', 'alya@email.com'),
	(3, 'Nia', 'Vebriani', 'armayndo@gmail.com'),
	(4, 'Romadhona', 'Armayndo', 'armayndo1@yahoo.com');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;

-- Dumping data for table spring_security_custom_user_demo.role: ~3 rows (approximately)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `name`) VALUES
	(1, 'ROLE_EMPLOYEE'),
	(2, 'ROLE_MANAGER'),
	(3, 'ROLE_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- Dumping data for table spring_security_custom_user_demo.user: ~5 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `username`, `password`, `first_name`, `last_name`, `email`) VALUES
	(1, 'emp', '$2a$10$dz.C3hCWEu0pLCPHnbkTv.WM/iyxnSTrn/MEmqXmdBMnKFm6Mkb5S', 'John cena', 'roma123', 'john@luv2code.com'),
	(2, 'man', '$2a$10$dz.C3hCWEu0pLCPHnbkTv.WM/iyxnSTrn/MEmqXmdBMnKFm6Mkb5S', 'Mary', 'Public', 'mary@luv2code.com'),
	(3, 'adm', '$2a$10$dz.C3hCWEu0pLCPHnbkTv.WM/iyxnSTrn/MEmqXmdBMnKFm6Mkb5S', 'Susan', 'Adams', 'susan@luv2code.com'),
	(4, 'roma', '$2a$10$dz.C3hCWEu0pLCPHnbkTv.WM/iyxnSTrn/MEmqXmdBMnKFm6Mkb5S', 'romadhona', 'armayndo', 'roma123@yahoo.com'),
	(14, 'roma1', '123', 'Nia', 'Vebriani', 'armayndo@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping data for table spring_security_custom_user_demo.users_roles: ~9 rows (approximately)
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
	(1, 1),
	(2, 1),
	(2, 2),
	(3, 1),
	(3, 3),
	(4, 1),
	(4, 2),
	(4, 3),
	(14, 1);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
