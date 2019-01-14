import mongoose, { Schema } from 'mongoose'

const tratamientoSchema = new Schema({
  periodo_toma: {
    type: String
  },
  fecha_inicio: {
    type: String
  },
  fecha_final: {
    type: String
  }
}, {
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

tratamientoSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      periodo_toma: this.periodo_toma,
      fecha_inicio: this.fecha_inicio,
      fecha_final: this.fecha_final,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Tratamiento', tratamientoSchema)

export const schema = model.schema
export default model
