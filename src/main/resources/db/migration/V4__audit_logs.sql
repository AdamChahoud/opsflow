CREATE TABLE audit_logs (
    id UUID PRIMARY KEY ,
    entity_type VARCHAR(50) NOT NULL,
    entity_id UUID,
    action VARCHAR(100) NOT NULL,
    performed_by UUID,
    metadata JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);