-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-04-2024 a las 21:03:39
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tpv`
--
CREATE DATABASE IF NOT EXISTS `tpv` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `tpv`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articles`
--

CREATE TABLE `articles` (
  `article_id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `family_id` varchar(50) DEFAULT NULL,
  `category_id` varchar(50) DEFAULT NULL,
  `purchase_base_price` decimal(20,6) NOT NULL,
  `sale_base_price` decimal(20,6) NOT NULL,
  `vat_id` varchar(50) NOT NULL,
  `stock` decimal(20,6) DEFAULT NULL,
  `sold` decimal(20,6) DEFAULT NULL,
  `offer_start_date` date DEFAULT NULL,
  `offer_end_date` date DEFAULT NULL,
  `offer_sale_base_price` decimal(20,6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `articles`
--

INSERT INTO `articles` (`article_id`, `name`, `family_id`, `category_id`, `purchase_base_price`, `sale_base_price`, `vat_id`, `stock`, `sold`, `offer_start_date`, `offer_end_date`, `offer_sale_base_price`) VALUES
('COD000001', 'Producto A', 'FAM000001', 'CAT000001', 0.000000, 15.990000, 'IVA001', 100.000000, 20.000000, '2024-04-01', '2024-04-15', 12.990000),
('COD000002', 'Producto B', 'FAM000002', 'CAT000002', 0.000000, 25.490000, 'IVA002', 50.000000, 10.000000, NULL, NULL, NULL),
('COD000003', 'Producto C', 'FAM000001', 'CAT000003', 0.000000, 9.990000, 'IVA001', 80.000000, 5.000000, NULL, NULL, NULL),
('COD000004', 'Producto D', 'FAM000003', 'CAT000002', 0.000000, 39.990000, 'IVA002', 30.000000, 8.000000, '2024-03-15', '2024-03-30', 34.990000),
('COD000005', 'Producto E', 'FAM000002', 'CAT000001', 0.000000, 19.990000, 'IVA001', 120.000000, 15.000000, NULL, NULL, NULL),
('COD000006', 'Producto F', 'FAM000003', 'CAT000002', 0.000000, 29.990000, 'IVA001', 60.000000, 12.000000, '2024-04-01', '2024-04-15', 24.990000),
('COD000007', 'Producto G', 'FAM000001', 'CAT000003', 0.000000, 49.990000, 'IVA002', 40.000000, 3.000000, NULL, NULL, NULL),
('COD000008', 'Producto H', 'FAM000002', 'CAT000003', 0.000000, 14.990000, 'IVA001', 90.000000, 10.000000, NULL, NULL, NULL),
('COD000009', 'Producto I', 'FAM000003', 'CAT000001', 0.000000, 35.990000, 'IVA002', 70.000000, 6.000000, NULL, NULL, NULL),
('COD000010', 'Producto J', 'FAM000001', 'CAT000002', 0.000000, 12.490000, 'IVA001', 110.000000, 18.000000, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `barcodes`
--

CREATE TABLE `barcodes` (
  `barcode` varchar(50) NOT NULL,
  `article_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `barcodes`
--

INSERT INTO `barcodes` (`barcode`, `article_id`) VALUES
('8999900000017', 'COD000001'),
('8999900000024', 'COD000001'),
('8999900000031', 'COD000001'),
('8999900000048', 'COD000002'),
('8999900000055', 'COD000002'),
('8999900000062', 'COD000002'),
('8999900000079', 'COD000003'),
('8999900000086', 'COD000003'),
('8999900000093', 'COD000003'),
('8999900000109', 'COD000004');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `customers_types`
--

CREATE TABLE `customers_types` (
  `customer_type_id` varchar(50) NOT NULL,
  `description` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `customers_types`
--

INSERT INTO `customers_types` (`customer_type_id`, `description`) VALUES
('CUST_TYPE001', 'Cliente anónimo'),
('CUST_TYPE002', 'Cliente VIP'),
('CUST_TYPE003', 'Cliente fiscal');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `taxable_customers`
--

CREATE TABLE `taxable_customers` (
  `customer_tax_id` varchar(50) NOT NULL,
  `legal_company_name` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `legal_company_address` varchar(50) NOT NULL,
  `legal_country` varchar(50) NOT NULL,
  `legal_location` varchar(50) NOT NULL,
  `legal_zip_code` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `taxable_customers`
--

INSERT INTO `taxable_customers` (`customer_tax_id`, `legal_company_name`, `name`, `legal_company_address`, `legal_country`, `legal_location`, `legal_zip_code`) VALUES
('A11223344', 'company 111', NULL, 'abc', 'cba', 'acb', 'bca'),
('A12345678', 'Tech Solutions Inc.', 'Tech Solutions', 'Calle Principal, 3', 'España', 'Sevilla', '80088'),
('A13334455', 'Data Innovations', NULL, '852 Maple Avenue', 'Spain', 'Madrid', '28001'),
('A14432211', 'Global Ventures', NULL, '321 Pine Lane', 'Australia', 'Sydney', '2000'),
('A15678900', 'Tech Solutions2', NULL, '456 Elm Avenue', 'Canada', 'Toronto', 'M5A 1E1'),
('A15678901', 'Tech Solutions', NULL, '456 Elm Avenue', 'Canada', 'Toronto', 'M5A 1E1'),
('a15678912', 'Soluciones Informáticas SL', 'Soluciones Informáticas', 'Calle Mayor, 22', 'España', 'Madrid', '33255'),
('A17654321', 'Consultoría y Servicios SA de CV', 'Consultoría y Servicios', 'Av. Reforma, 555', 'España', 'Ciudad Real', '25522'),
('A18887777', 'Digital Creations', NULL, '741 Birch Street', 'France', 'Paris', '75001'),
('A18889999', 'Future Trends', NULL, '369 Walnut Street', 'Italy', 'Rome', '00184'),
('A19988776', 'Innovative Designs', NULL, '789 Oak Street', 'UK', 'London', 'SW1A 1AA'),
('A19998765', 'Innovative Tech', NULL, '987 Cedar Road', 'Germany', 'Berlin', '10115'),
('D89123456', 'Consultoría Integral Argentina', 'Consultoría Integral', 'Av. Corrientes, 123', 'España', 'Cáceres', '55664'),
('E32165498', 'Services Informatiques SAS', 'Services Informatiques', 'Calle De La Paz, 7', 'España', 'Almería', '22122');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tickets`
--

CREATE TABLE `tickets` (
  `ticket_id` varchar(50) NOT NULL,
  `sale_date` date NOT NULL,
  `customer_tax_id` varchar(50) DEFAULT NULL,
  `ticket_status_id` varchar(50) NOT NULL,
  `payment_method_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tickets_lines`
--

CREATE TABLE `tickets_lines` (
  `ticket_line_id` varchar(50) NOT NULL,
  `ticket_id` varchar(50) NOT NULL,
  `article_id` varchar(50) NOT NULL,
  `article_quantity` decimal(20,6) NOT NULL,
  `purchase_base_price_at_sale_moment` decimal(20,6) NOT NULL,
  `sale_base_price_at_sale_moment` decimal(20,6) NOT NULL,
  `vat_id` varchar(50) NOT NULL,
  `is_in_offer` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `user_id` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `privileges` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`user_id`, `password`, `privileges`) VALUES
('anonimos', 'anonimos', 'basic'),
('basic', 'basic', 'basic'),
('carlos', 'carlos', 'admin'),
('manager', 'manager', 'manager'),
('super', 'super', 'super');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vats`
--

CREATE TABLE `vats` (
  `vat_id` varchar(50) NOT NULL,
  `description` varchar(50) DEFAULT NULL,
  `vat_fraction` decimal(20,6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vats`
--

INSERT INTO `vats` (`vat_id`, `description`, `vat_fraction`) VALUES
('IVA001', 'IVA GENERAL 21%', 0.210000),
('IVA002', 'IVA REDUCIDO 10%', 0.100000),
('IVA003', 'IVA SUPERREDUCIDO 5%', 0.050000),
('IVA004', 'IVA SUPERREDUCIDO 4%', 0.040000),
('IVA005', 'IVA SUPERREDUCIDO 0%', 0.000000),
('IVA006', 'SIN IVA', 0.000000);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articles`
--
ALTER TABLE `articles`
  ADD PRIMARY KEY (`article_id`) USING BTREE,
  ADD KEY `vat_id` (`vat_id`);

--
-- Indices de la tabla `barcodes`
--
ALTER TABLE `barcodes`
  ADD PRIMARY KEY (`barcode`),
  ADD KEY `internal_code` (`article_id`) USING BTREE;

--
-- Indices de la tabla `customers_types`
--
ALTER TABLE `customers_types`
  ADD PRIMARY KEY (`customer_type_id`) USING BTREE;

--
-- Indices de la tabla `taxable_customers`
--
ALTER TABLE `taxable_customers`
  ADD PRIMARY KEY (`customer_tax_id`) USING BTREE,
  ADD UNIQUE KEY `legal_company_name` (`legal_company_name`);

--
-- Indices de la tabla `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`ticket_id`),
  ADD KEY `customer_tax_id` (`customer_tax_id`);

--
-- Indices de la tabla `tickets_lines`
--
ALTER TABLE `tickets_lines`
  ADD PRIMARY KEY (`ticket_line_id`),
  ADD KEY `ticket_id` (`ticket_id`),
  ADD KEY `vat_id` (`vat_id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- Indices de la tabla `vats`
--
ALTER TABLE `vats`
  ADD PRIMARY KEY (`vat_id`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `articles`
--
ALTER TABLE `articles`
  ADD CONSTRAINT `articles_ibfk_1` FOREIGN KEY (`vat_id`) REFERENCES `vats` (`vat_id`);

--
-- Filtros para la tabla `barcodes`
--
ALTER TABLE `barcodes`
  ADD CONSTRAINT `barcodes_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `articles` (`article_id`);

--
-- Filtros para la tabla `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `tickets_ibfk_1` FOREIGN KEY (`customer_tax_id`) REFERENCES `taxable_customers` (`customer_tax_id`);

--
-- Filtros para la tabla `tickets_lines`
--
ALTER TABLE `tickets_lines`
  ADD CONSTRAINT `tickets_lines_ibfk_1` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`ticket_id`),
  ADD CONSTRAINT `tickets_lines_ibfk_2` FOREIGN KEY (`vat_id`) REFERENCES `vats` (`vat_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
