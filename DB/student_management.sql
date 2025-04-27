-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2025 at 06:42 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `student_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `announcements`
--

CREATE TABLE `announcements` (
  `id` int(11) NOT NULL,
  `message` text DEFAULT NULL,
  `target_group` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `announcements`
--

INSERT INTO `announcements` (`id`, `message`, `target_group`) VALUES
(1, '📢 Exam next week!', 'students'),
(2, '📢 Sports event on Friday!', 'all');

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

CREATE TABLE `attendance` (
  `student_id` int(11) NOT NULL,
  `present_percent` decimal(5,2) DEFAULT NULL,
  `absent_percent` decimal(5,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `attendance`
--

INSERT INTO `attendance` (`student_id`, `present_percent`, `absent_percent`) VALUES
(1, 85.00, 15.00);

-- --------------------------------------------------------

--
-- Table structure for table `attendance_records`
--

CREATE TABLE `attendance_records` (
  `id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `attendance_date` date NOT NULL,
  `status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `attendance_records`
--

INSERT INTO `attendance_records` (`id`, `student_id`, `attendance_date`, `status`) VALUES
(1, 1, '2025-01-01', 'Present'),
(2, 1, '2025-01-02', 'Present'),
(3, 1, '2025-01-06', 'Absent'),
(4, 1, '2025-01-07', 'Present'),
(5, 1, '2025-01-08', 'Present'),
(6, 1, '2025-01-09', 'Present'),
(7, 1, '2025-01-13', 'Present'),
(8, 1, '2025-01-14', 'Late'),
(9, 1, '2025-01-15', 'Present'),
(10, 1, '2025-01-16', 'Absent'),
(11, 1, '2025-01-20', 'Present'),
(12, 1, '2025-01-21', 'Present'),
(13, 1, '2025-01-22', 'Late'),
(14, 1, '2025-01-23', 'Present'),
(15, 1, '2025-01-27', 'Absent'),
(16, 1, '2025-01-28', 'Present'),
(17, 1, '2025-01-29', 'Present'),
(18, 1, '2025-01-30', 'Present'),
(19, 1, '2025-02-03', 'Present'),
(20, 1, '2025-02-04', 'Present'),
(21, 1, '2025-02-05', 'Absent'),
(22, 1, '2025-02-06', 'Present'),
(23, 1, '2025-02-10', 'Late'),
(24, 1, '2025-02-11', 'Present'),
(25, 1, '2025-02-12', 'Present'),
(26, 1, '2025-02-13', 'Present'),
(27, 1, '2025-02-17', 'Present'),
(28, 1, '2025-02-18', 'Present'),
(29, 1, '2025-02-19', 'Absent'),
(30, 1, '2025-02-20', 'Present'),
(31, 1, '2025-02-24', 'Late'),
(32, 1, '2025-02-25', 'Present'),
(33, 1, '2025-02-26', 'Present'),
(34, 1, '2025-02-27', 'Present'),
(35, 1, '2025-03-03', 'Present'),
(36, 1, '2025-03-04', 'Present'),
(37, 1, '2025-03-05', 'Present'),
(38, 1, '2025-03-06', 'Late'),
(39, 1, '2025-03-10', 'Present'),
(40, 1, '2025-03-11', 'Absent'),
(41, 1, '2025-03-12', 'Present'),
(42, 1, '2025-03-13', 'Present'),
(43, 1, '2025-03-17', 'Present'),
(44, 1, '2025-03-18', 'Present'),
(45, 1, '2025-03-19', 'Present'),
(46, 1, '2025-03-20', 'Present'),
(47, 1, '2025-03-24', 'Absent'),
(48, 1, '2025-03-25', 'Present'),
(49, 1, '2025-03-26', 'Present'),
(50, 1, '2025-03-27', 'Present');

-- --------------------------------------------------------

--
-- Table structure for table `grades`
--

CREATE TABLE `grades` (
  `id` int(11) NOT NULL,
  `student_id` int(11) DEFAULT NULL,
  `subject` varchar(100) DEFAULT NULL,
  `grade` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `grades`
--

INSERT INTO `grades` (`id`, `student_id`, `subject`, `grade`) VALUES
(1, 1, 'Math', 'A'),
(2, 1, 'Science', 'B+'),
(3, 1, 'History', 'A-');

-- --------------------------------------------------------

--
-- Table structure for table `result`
--

CREATE TABLE `result` (
  `id` int(11) NOT NULL,
  `student_id` int(11) DEFAULT NULL,
  `semester` varchar(10) DEFAULT NULL,
  `subject` varchar(100) DEFAULT NULL,
  `grade_letter` varchar(2) DEFAULT NULL,
  `grade_point` decimal(3,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `result`
--

INSERT INTO `result` (`id`, `student_id`, `semester`, `subject`, `grade_letter`, `grade_point`) VALUES
(1, 1, 'Sem 1', 'Math 101', 'A', 4.00),
(2, 1, 'Sem 1', 'Physics 101', 'B+', 3.50),
(3, 1, 'Sem 1', 'English 101', 'A-', 3.67),
(4, 1, 'Sem 2', 'Math 102', 'B', 3.00),
(5, 1, 'Sem 2', 'Chemistry 101', 'A', 4.00),
(6, 1, 'Sem 2', 'History 101', 'B+', 3.50),
(7, 1, 'Sem 3', 'CS 201', 'A-', 3.67),
(8, 1, 'Sem 3', 'Biology 101', 'B', 3.00),
(9, 1, 'Sem 3', 'Ethics 101', 'A', 4.00);

-- --------------------------------------------------------

--
-- Table structure for table `schedules`
--

CREATE TABLE `schedules` (
  `id` int(11) NOT NULL,
  `student_id` int(11) DEFAULT NULL,
  `subject` varchar(100) DEFAULT NULL,
  `time` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `schedules`
--

INSERT INTO `schedules` (`id`, `student_id`, `subject`, `time`) VALUES
(1, 1, 'Math', '10:00:00'),
(2, 1, 'Science', '12:00:00'),
(3, 1, 'History', '14:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `semester_results`
--

CREATE TABLE `semester_results` (
  `id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `semester` varchar(20) NOT NULL,
  `semester_gpa` decimal(3,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `semester_results`
--

INSERT INTO `semester_results` (`id`, `student_id`, `semester`, `semester_gpa`) VALUES
(1, 1, 'Sem 1', 3.72),
(2, 1, 'Sem 2', 3.50),
(3, 1, 'Sem 3', 3.56);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `student_id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`student_id`, `name`, `email`) VALUES
(1, 'Muhiminul', 'muhiminul@example.com');

-- --------------------------------------------------------

--
-- Table structure for table `weekly_schedule`
--

CREATE TABLE `weekly_schedule` (
  `id` int(11) NOT NULL,
  `student_id` int(11) DEFAULT NULL,
  `day_of_week` varchar(20) DEFAULT NULL,
  `period_number` int(2) DEFAULT NULL,
  `subject` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `weekly_schedule`
--

INSERT INTO `weekly_schedule` (`id`, `student_id`, `day_of_week`, `period_number`, `subject`) VALUES
(1, 1, 'Monday', 1, 'Math'),
(2, 1, 'Monday', 2, 'Physics'),
(3, 1, 'Monday', 3, 'Break'),
(4, 1, 'Monday', 4, 'Computer Science'),
(5, 1, 'Tuesday', 1, 'Science'),
(6, 1, 'Tuesday', 2, 'Break'),
(7, 1, 'Tuesday', 3, 'Math'),
(8, 1, 'Tuesday', 4, 'English'),
(9, 1, 'Wednesday', 1, 'English'),
(10, 1, 'Wednesday', 2, 'Chemistry'),
(11, 1, 'Wednesday', 3, 'Break'),
(12, 1, 'Wednesday', 4, 'Physics'),
(13, 1, 'Thursday', 1, 'History'),
(14, 1, 'Thursday', 2, 'Biology'),
(15, 1, 'Thursday', 3, 'Break'),
(16, 1, 'Thursday', 4, 'Math');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `announcements`
--
ALTER TABLE `announcements`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `attendance`
--
ALTER TABLE `attendance`
  ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `attendance_records`
--
ALTER TABLE `attendance_records`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `grades`
--
ALTER TABLE `grades`
  ADD PRIMARY KEY (`id`),
  ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `result`
--
ALTER TABLE `result`
  ADD PRIMARY KEY (`id`),
  ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `schedules`
--
ALTER TABLE `schedules`
  ADD PRIMARY KEY (`id`),
  ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `semester_results`
--
ALTER TABLE `semester_results`
  ADD PRIMARY KEY (`id`),
  ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `weekly_schedule`
--
ALTER TABLE `weekly_schedule`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `announcements`
--
ALTER TABLE `announcements`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `attendance_records`
--
ALTER TABLE `attendance_records`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `grades`
--
ALTER TABLE `grades`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `result`
--
ALTER TABLE `result`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `schedules`
--
ALTER TABLE `schedules`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `semester_results`
--
ALTER TABLE `semester_results`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `student_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `weekly_schedule`
--
ALTER TABLE `weekly_schedule`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `attendance`
--
ALTER TABLE `attendance`
  ADD CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`);

--
-- Constraints for table `grades`
--
ALTER TABLE `grades`
  ADD CONSTRAINT `grades_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`);

--
-- Constraints for table `result`
--
ALTER TABLE `result`
  ADD CONSTRAINT `result_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`);

--
-- Constraints for table `schedules`
--
ALTER TABLE `schedules`
  ADD CONSTRAINT `schedules_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`);

--
-- Constraints for table `semester_results`
--
ALTER TABLE `semester_results`
  ADD CONSTRAINT `semester_results_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
