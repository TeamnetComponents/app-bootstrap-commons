package ro.teamnet.bootstrap.plugin.upload;

import ro.teamnet.bootstrap.service.AbstractService;

import java.io.Serializable;

public interface BaseFileService<T extends Serializable> extends AbstractService<T,Long> {
}
