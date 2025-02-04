# CLASSRD
    INSERT INTO <SCH>CLASSRD (CLASS_NAME, RELATION_NAME, CORR_CLASS_NAME, OUTER_JOIN, ONLY_SELECT, XMLEXPORTABLE, GRID_DISPLAYABLE, DEF_SETTING_POS, USER_ID, CHECK_DISABLED) VALUES ('YModificaConfRigheVen', 'Configurazione', 'ConfigurazioneRicEnh', 'Y', 'N', 'N', 'Y', 0, <USER>, 'N');
# CLASSRD_NLS
    INSERT INTO <SCH>CLASSRD_NLS (CLASS_NAME, RELATION_NAME, LANGUAGE, NLS_RELATION_NAME, NLS_COLUMN_TITLE, NLS_LABEL) VALUES ('YModificaConfRigheVen', 'Configurazione', 'it', 'Configurazione', 'Configurazione', null);
# CLASSRD_ROW
    INSERT INTO <SCH>CLASSRD_ROW (CLASS_NAME, RELATION_NAME, SEQUENCE, ATTRIBUTE_NAME, CD_NAME, CORR_CLASS_NAME, CORR_ATTR_NAME, DEF_SEARCH) VALUES ('YModificaConfRigheVen', 'Configurazione', 4, null, 'Descrizione.Descrizione', 'DescrizioneEstesa1000', 'Descrizione', 'Y');
    INSERT INTO <SCH>CLASSRD_ROW (CLASS_NAME, RELATION_NAME, SEQUENCE, ATTRIBUTE_NAME, CD_NAME, CORR_CLASS_NAME, CORR_ATTR_NAME, DEF_SEARCH) VALUES ('YModificaConfRigheVen', 'Configurazione', 2, 'IdArticolo', null, 'ConfigurazioneRicEnh', 'IdArticolo', 'N');
    INSERT INTO <SCH>CLASSRD_ROW (CLASS_NAME, RELATION_NAME, SEQUENCE, ATTRIBUTE_NAME, CD_NAME, CORR_CLASS_NAME, CORR_ATTR_NAME, DEF_SEARCH) VALUES ('YModificaConfRigheVen', 'Configurazione', 1, 'IdAzienda', null, 'ConfigurazioneRicEnh', 'IdAzienda', 'N');
    INSERT INTO <SCH>CLASSRD_ROW (CLASS_NAME, RELATION_NAME, SEQUENCE, ATTRIBUTE_NAME, CD_NAME, CORR_CLASS_NAME, CORR_ATTR_NAME, DEF_SEARCH) VALUES ('YModificaConfRigheVen', 'Configurazione', 3, 'IdEsternoConfig', null, 'ConfigurazioneRicEnh', 'IdEsternoConfig', 'N');
