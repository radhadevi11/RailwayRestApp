package com.radha.railwayrest;

import com.radha.railwayrest.db.entity.StationEntity;
import com.radha.railwayrest.db.entity.TrainEntity;
import com.radha.railwayrest.db.entity.TrainStopEntity;
import com.radha.railwayrest.db.repo.StationRepo;
import com.radha.railwayrest.db.repo.TrainRepo;
import com.radha.railwayrest.db.repo.TrainStopRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RailwayrestApplication {
	private static final Logger log = LoggerFactory.getLogger(RailwayrestApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(RailwayrestApplication.class, args);
	}
	@Bean
	public CommandLineRunner demoForStationRepo(StationRepo repository) {
		return (args) -> {
			log.info("Stations found with findAll():");
			log.info("-------------------------------");
			for (StationEntity station : repository.findAll()) {
				log.info(station.toString());
			}
			log.info("Save Station");
			repository.save(new StationEntity("dummy name2", "efg"));
			log.info("-------------------------------");

			StationEntity station = repository.findById(10);
			log.info("Station found with findById(10):");
			log.info("--------------------------------");
			log.info(station.toString());

			StationEntity stationByStationCode = repository.findByCode("MAS");
			log.info("Station found with findByCode(MAS):");
			log.info("--------------------------------");
			log.info(stationByStationCode.toString());

			};

	};

	@Bean
	public CommandLineRunner demoForTrainRepo(TrainRepo repository) {
		return (args) -> {
			log.info("Train found with findById(10):");
			log.info("--------------------------------");
			repository.findById(10).ifPresent(train -> log.info(train.toString()));

			log.info("Train found with findBySourceStation(241):");
			log.info("--------------------------------");
			for(TrainEntity train : repository.findBySourceStation(new StationEntity(241))) {
				log.info(train.toString());

			}

			log.info("Train found with findBySourceStationId(241):");
			log.info("--------------------------------");
			for(TrainEntity train : repository.findBySourceStationId(241)) {
				log.info(train.toString());

			}

		};

	};
	@Bean
	public CommandLineRunner demoForTrainStopRepo(TrainStopRepo repository) {
		return (args) -> {
			log.info("Train Stop found with findById(10):");
			log.info("--------------------------------");
			repository.findById(10).ifPresent(trainStop -> log.info(trainStop.toString()));

			log.info("Train found with findByTrainId(241):");
			log.info("--------------------------------");
			for(TrainStopEntity trainStop : repository.findByTrainId(4)) {
				log.info(trainStop.toString());

			}

			log.info("Train found with findBySourceStationId(241):");
			log.info("--------------------------------");
			for(TrainStopEntity trainStop : repository.findByStationId(391)) {
				log.info(trainStop.toString());

			}

		};

	};

}