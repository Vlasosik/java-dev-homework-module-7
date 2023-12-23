package org.example.dao;


import org.example.entity.Client;
import org.example.entity.Project;
import org.example.entity.ProjectWorker;
import org.example.entity.Worker;
import org.example.util.Database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DatabasePopulateService {
    private static final String INSERT_WORKERS = "INSERT INTO worker (NAME, BIRTHDAY, LEVEL, SALARY)" +
            "VALUES (?, ?, ?, ?)";
    private static final String INSERT_CLIENT = "INSERT INTO client (NAME)" +
            "VALUES (?)";
    private static final String INSERT_PROJECT = "INSERT INTO project (CLIENT_ID, START_DATE, FINISH_DATE)" +
            "VALUES (?, ?, ?)";
    private static final String INSERT_PROJECT_WORKER = "INSERT INTO project_worker (PROJECT_ID, WORKER_ID)" +
            "VALUES (?, ?)";

    public static void main(String[] args) throws SQLException {
        DatabasePopulateService databasePopulateService = new DatabasePopulateService();
        databasePopulateService.workersList();
        databasePopulateService.clientList();
        databasePopulateService.projectList();
        databasePopulateService.projectWorkerList();
    }

    private void workersList() throws SQLException {
        List<Worker> workers = Arrays.asList(
                new Worker("Nazar", LocalDate.parse("1999-02-11"), "MIDDLE", new BigDecimal("2000.00")),
                new Worker("Vlas", LocalDate.parse("2001-08-31"), "JUNIOR", new BigDecimal("1000.00")),
                new Worker("Pavlo", LocalDate.parse("1998-03-31"), "TRAINEE", new BigDecimal("1000.00")),
                new Worker("Denys", LocalDate.parse("1995-06-12"), "SENIOR", new BigDecimal("4000.00")),
                new Worker("Dmytro", LocalDate.parse("2002-02-19"), "JUNIOR", new BigDecimal("1000.00")),
                new Worker("Volodymyr", LocalDate.parse("1990-11-27"), "MIDDLE", new BigDecimal("3000.00")),
                new Worker("Sergei", LocalDate.parse("1995-12-04"), "SENIOR", new BigDecimal("3500.00")),
                new Worker("Oleksandr", LocalDate.parse("2002-05-28"), "TRAINEE", new BigDecimal("1000.00")),
                new Worker("Vadym", LocalDate.parse("1996-04-30"), "SENIOR", new BigDecimal("4000.00")),
                new Worker("Oleg", LocalDate.parse("2003-09-15"), "JUNIOR", new BigDecimal("1000.00"))
        );
        setInsertWorkers(workers);
    }

    private void setInsertWorkers(List<Worker> workers) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_WORKERS);
            for (Worker worker : workers) {
                preparedStatement.setString(1, worker.getName());
                preparedStatement.setDate(2, Date.valueOf(worker.getBirthday()));
                preparedStatement.setString(3, worker.getLevel());
                preparedStatement.setBigDecimal(4, worker.getSalary());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            connection.rollback();
        } finally {
            assert connection != null;
            connection.setAutoCommit(true);
        }
    }

    private void clientList() throws SQLException {
        List<Client> clients = Arrays.asList(
                new Client("Oliver"),
                new Client("Harry"),
                new Client("Charlie"),
                new Client("Thomas"),
                new Client("Oscar")
        );
        setInsertClient(clients);
    }

    private void setInsertClient(List<Client> clients) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENT);
            for (Client client : clients) {
                preparedStatement.setString(1, client.getName());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            connection.rollback();
        } finally {
            assert connection != null;
            connection.setAutoCommit(true);
        }
    }

    private void projectList() throws SQLException {
        List<Project> projects = Arrays.asList(
                new Project(1L, LocalDate.parse("2020-01-01"), LocalDate.parse("2023-04-01")),
                new Project(2L, LocalDate.parse("2022-02-15"), LocalDate.parse("2022-09-15")),
                new Project(3L, LocalDate.parse("2021-03-10"), LocalDate.parse("2022-06-10")),
                new Project(4L, LocalDate.parse("2020-04-05"), LocalDate.parse("2022-08-05")),
                new Project(5L, LocalDate.parse("2016-05-20"), LocalDate.parse("2022-07-20")),
                new Project(3L, LocalDate.parse("2020-06-15"), LocalDate.parse("2022-12-15")),
                new Project(2L, LocalDate.parse("2019-07-01"), LocalDate.parse("2022-10-01")),
                new Project(4L, LocalDate.parse("2017-08-08"), LocalDate.parse("2023-02-08")),
                new Project(1L, LocalDate.parse("2018-09-25"), LocalDate.parse("2023-11-25")),
                new Project(3L, LocalDate.parse("2022-10-10"), LocalDate.parse("2023-01-10"))
        );
        setInsertProject(projects);
    }

    private void setInsertProject(List<Project> projects) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROJECT);
            for (Project project : projects) {
                preparedStatement.setLong(1, project.getClientId());
                preparedStatement.setDate(2, Date.valueOf(project.getStartDate()));
                preparedStatement.setDate(3, Date.valueOf(project.getFinishDate()));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
            connection.rollback();
        } finally {
            assert connection != null;
            connection.setAutoCommit(true);
        }
    }

    private void projectWorkerList() throws SQLException {
        List<ProjectWorker> projectWorkers = Arrays.asList(
                new ProjectWorker(1L, 9L),
                new ProjectWorker(2L, 5L),
                new ProjectWorker(3L, 7L),
                new ProjectWorker(4L, 8L),
                new ProjectWorker(1L, 2L)
        );
        setInsertProjectWorker(projectWorkers);
    }

    private void setInsertProjectWorker(List<ProjectWorker> projectWorkers) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROJECT_WORKER);
            for (ProjectWorker projectWorker : projectWorkers) {
                preparedStatement.setLong(1, projectWorker.getProjectId());
                preparedStatement.setLong(2, projectWorker.getWorkerId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
            connection.rollback();
        } finally {
            assert connection != null;
            connection.setAutoCommit(true);
        }
    }
}





