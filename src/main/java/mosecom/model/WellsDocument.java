package mosecom.model;

import lombok.Data;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Files", schema = "public")
public class WellsDocument implements Serializable{

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "type") // TODO: переименовать в type_id
    protected DocumentType documentType;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_content_type")
    private String fileContentType;

    @ManyToOne
    @JoinColumn(name = "well_id", referencedColumnName = "Well_ID")
    protected Well well;
}
